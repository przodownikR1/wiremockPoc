package pl.java.scalatech.syntax
import java.lang.Void as Should
import spock.lang.Specification

class SygarSyntaxSpec extends Specification {

    Should 'reverse a string'() {
        expect:
        'Hello World!'.reverse() == '!dlroW olleH'
    }

    Should 'remove two letters'() {
        expect:
        'AXXBC' - 'XX' == 'ABC'
    }

    Should 'pattern twice'() {
        expect:
        'ABC' * 2 == 'ABCABC'
    }

    Should 'take some chars'() {
        expect:
        '!abcdefg!'[1..7] == 'abcdefg'
        '!abcdefg!'.drop(1) == 'abcdefg!'
        '!abcdefg!'.take(8) == '!abcdefg'
    }

    Should 'get first 5 letters from alphabet'() {
        given:
        def alphabet = 'e'..'i'

        expect:
        alphabet.size() == 5
        alphabet == ['e', 'f', 'g', 'h', 'i']
    }

    Should 'should get map values'() {
        given:
        def map = [color: 'Green',shape: 'Circle', price: 50]

        expect:
        map.color == 'Green'
        map.shape == 'Circle'
        map.price == 10 * 5
    }
}
